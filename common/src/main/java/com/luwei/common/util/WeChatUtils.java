package com.luwei.common.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.exception.ValidationException;
import com.luwei.common.property.ShareParam;
import com.luwei.common.property.WechatPayApply;
import com.luwei.common.property.WechatPayPackage;
import com.luwei.common.property.WechatPayRefund;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class WeChatUtils {
    private static final Logger logger = LoggerFactory.getLogger(WeChatUtils.class);

    @Value("${luwei.module.wx.appId}")
    private String appId;

    @Value("${luwei.module.wx.appSecret}")
    private String appSecret;

    @Value("${wechat.mchKey}")
    private String mchKey;

    @Value("${wechat.mchId}")
    private String mchId;

    //订单生成的ip
    @Value("${wechat.spbillCreateIp}")
    private String spbillCreateIp;

    //接口调用凭证--两小时刷新一次
    public static String TOKEN = "";

    //js调用凭证 --两小时刷新一次
    public static String TICKET = "";

//-------------------------------------------------------微信登录授权，获取用户信息开始---------------------------------------------

    /**
     * 利用code 和 state 获取 access_token 和 open_id
     */
    public Map<String, Object> authorize(String code) {
        logger.info(appId);
        logger.info(appSecret);
        Map<String, Object> params = new HashMap<>();
        params.put("appid", appId);
        params.put("secret", appSecret);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        //return getWechatReturn(params, url);
        return null;
    }

    /**
     * 通过 accessToken 和 openId获取用户个人信息
     */
    public Map<String, Object> getUserInfo(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo";
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("openid", openId);
        params.put("lang", "zh-CN");
        return getWechatReturn(params, url);
    }

    public Map<String, Object> getUserInfoWithUnionid(String openId) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info";
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", WeChatUtils.TOKEN);
        params.put("openid", openId);
        params.put("lang", "zh-CN");
        Map<String, Object> wechatReturn = getWechatReturn(params, url);
        if (wechatReturn.containsKey("errcode") && Objects.equals(wechatReturn.get("errcode"), "40001")) {
            getToken();
            params.put("access_token", WeChatUtils.TOKEN);
            wechatReturn = getWechatReturn(params, url);
        }
        return wechatReturn;
    }
//--------------------------------------微信登录授权，获取用户信息结束------------------------------------------------------------

//--------------------------------------模板消息开始------------------------------------------------------------

    /**
     * 从行业模板库选择模板到帐号后台
     *
     * @param templateIdShort 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
     * @return 返回
     * {
     * "errcode":0,
     * "errmsg":"ok",
     * "template_id":"Doclyl5uP7Aciu-qZ7mJNPtWkbkYnWBWVja26EGbNyk"
     * }
     */
    public Map apiAddTemplate(String templateIdShort) {
        String url = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=" + WeChatUtils.TOKEN;

        Map<String, Object> param = new HashMap<>();
        param.put("template_id_short", templateIdShort);
        String request = post(url, param);
        if (StringUtils.isEmpty(request)) {
            return null;
        }

        return new Gson().fromJson(request, Map.class);
    }

    /**
     * 发送模板消息
     *
     * @param openId     接收者openid
     * @param templateId 模板ID
     * @param url        模板跳转链接(非必须)
     * @param data       模板数据
     * @return {
     * "errcode":0,
     * "errmsg":"ok",
     * "msgid":200228332
     * }
     */
    public Map sendTemplateMsg(String openId, String templateId, String url,
                               Map<String, Map> data) {

        Map<String, Object> param = new HashMap<>();
        param.put("touser", openId);
        param.put("template_id", templateId);
        param.put("url", url);
        param.put("data", data);

        String request = post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + WeChatUtils.TOKEN, param);
        if (StringUtils.isEmpty(request)) {
            return null;
        }

        return new Gson().fromJson(request, Map.class);
    }
//--------------------------------------模板消息结束------------------------------------------------------------

//--------------------------------------客服接口-发消息开始------------------------------------------------------------

    /**
     * 发送文本消息
     *
     * @param openId  接受者openId
     * @param content 发送内容
     */
    public void sendText(String openId, String content) {
        Map<String, Object> param = new HashMap<>();
        param.put("touser", openId);
        param.put("msgtype", "text");
        HashMap<Object, Object> text = new HashMap<>();
        text.put("content", content);
        param.put("text", text);

        post("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + WeChatUtils.TOKEN, param);
    }

    /**
     * 发送文本消息
     *
     * @param openId  接受者openId
     * @param mediaId 发送内容
     */
    public String sendImage(String openId, String mediaId) {
        Map<String, Object> param = new HashMap<>();
        param.put("touser", openId);
        param.put("msgtype", "image");
        HashMap<Object, Object> image = new HashMap<>();
        image.put("media_id", mediaId);
        param.put("image", image);

        return post("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + WeChatUtils.TOKEN, param);
    }
//--------------------------------------客服接口-发消息结束------------------------------------------------------------

//--------------------------------------新增其他类型永久素材开始------------------------------------------------------------

    /**
     * 新增其他类型永久素材
     *
     * @param type          媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     * @param multipartFile form-data中媒体文件标识，有filename、filelength、content-type等信息
     */
    public void addMaterial(String type, MultipartFile multipartFile) {
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("type", type);
        param.add("media", multipartFile);

        RestTemplate rest = new RestTemplate();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param);
        ResponseEntity<String> entity = rest.exchange("https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + WeChatUtils.TOKEN,
                HttpMethod.POST, httpEntity, String.class);
        String request = entity.getBody();
        logger.debug(request);
    }

    /**
     * 上传图片到微信服务器（不是保存在公众号的素材库，因为该库仅限5000张）
     *
     * @param multipartFile
     */
    public Map uploadMedia(String type, MultipartFile multipartFile) {
        StringBuffer buffer = new StringBuffer();
        try {
            //1.建立连接
            String reqUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=PARAM_TYPE";//微信上传图片的url
            reqUrl = reqUrl.replaceAll("ACCESS_TOKEN", WeChatUtils.TOKEN);
            reqUrl = reqUrl.replaceAll("PARAM_TYPE", type);
            URL url = new URL(reqUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  //打开链接

            //1.1输入输出设置
            httpUrlConn.setDoInput(true);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setUseCaches(false); // post方式不能使用缓存
            //1.2设置请求头信息
            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            //1.3设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            httpUrlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            // 请求正文信息
            // 第一部分：
            //2.将文件头输出到微信服务器
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + multipartFile.getSize()
                    + "\";filename=\"" + multipartFile.getOriginalFilename() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            OutputStream outputStream = new DataOutputStream(httpUrlConn.getOutputStream());
            // 将表头写入输出流中：输出表头
            outputStream.write(head);

            //3.将文件正文部分输出到微信服务器
            // 把文件以流文件的方式 写入到微信服务器中
            byte[] bytes1 = multipartFile.getBytes();
            outputStream.write(bytes1, 0, bytes1.length);
            //4.将结尾部分输出到微信服务器
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            outputStream.write(foot);
            outputStream.flush();
            outputStream.close();

            //5.将微信服务器返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            httpUrlConn.disconnect();
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        return new Gson().fromJson(buffer.toString(), Map.class);
    }

//--------------------------------------新增其他类型永久素材结束------------------------------------------------------------

//--------------------------------------刷新调用微信接口的access_token开始---------------------------------------------------

    /**
     * 获取accesstoken,每隔1小时执行一次
     */
//    @Scheduled(cron = "0 0 0/1 * * ?")
    public void getToken() {
        logger.info("-------" + appId);
        logger.info("-------" + appSecret);
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "client_credential");
        params.put("appid", appId);
        params.put("secret", appSecret);
        Map<String, Object> tokenMap = getWechatReturn(params, url);
        WeChatUtils.TOKEN = (String) tokenMap.get("access_token");
        Map<String, Object> ticketMap = getTicket(WeChatUtils.TOKEN);
        WeChatUtils.TICKET = (String) ticketMap.get("ticket");
        logger.info("------token:{}-----", WeChatUtils.TOKEN);
        logger.info("-----ticket:{}-----", WeChatUtils.TICKET);
    }

//--------------------------------------刷新调用微信接口的access_token结束---------------------------------------------------

//--------------------------------------生成带参数二维码开始---------------------------------------------------

    /**
     * 生成临时二维码
     *
     * @param expireSeconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒
     * @param sceneStr      场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
     * @return
     */
    public WeiXinQRCode createTempQRCode(int expireSeconds, String sceneStr) {

        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + WeChatUtils.TOKEN;

        Map<String, Object> param = new HashMap<>();
        param.put("expire_seconds", expireSeconds);
        param.put("action_name", "QR_STR_SCENE");//临时的字符串参数值
        Map<String, Object> actionInfoMap = new HashMap<>();
        Map<String, String> sceneMap = new HashMap<>();
        sceneMap.put("scene_str", sceneStr);
        actionInfoMap.put("scene", sceneMap);
        param.put("action_info", actionInfoMap);

        String request = post(url, param);
        if (StringUtils.isEmpty(request)) {
            return null;
        }

        return JSONObject.parseObject(request, WeiXinQRCode.class);
    }

    /**
     * 生成永久二维码
     *
     * @param sceneStr 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
     * @return
     */
    public WeiXinQRCode createQRCode(String sceneStr) {

        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + WeChatUtils.TOKEN;

        Map<String, Object> param = new HashMap<>();
        param.put("action_name", "QR_LIMIT_STR_SCENE");//永久的字符串参数值
        Map<String, Object> actionInfoMap = new HashMap<>();
        Map<String, String> sceneMap = new HashMap<>();
        sceneMap.put("scene_str", sceneStr);
        actionInfoMap.put("scene", sceneMap);
        param.put("action_info", actionInfoMap);

        String request = post(url, param);
        if (StringUtils.isEmpty(request)) {
            return null;
        }

        return JSONObject.parseObject(request, WeiXinQRCode.class);
    }

    private String post(String url, Map<String, Object> param) {
        RestTemplate rest = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> entity = rest.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String request = entity.getBody();
        if (request.contains("40001")) {//获取access_token时AppSecret错误，或者access_token无效,再试一次
            this.getToken();
            entity = rest.exchange(url, HttpMethod.POST, requestEntity, String.class);
            request = entity.getBody();
        }
        logger.debug(request);
        return request;
    }
//--------------------------------------生成带参数二维码结束---------------------------------------------------

//--------------------------------------刷新调用微信接口的票据ticket开始---------------------------------------------------

    /**
     * 获得 js_api ticket
     */
    public Map<String, Object> getTicket(String token) {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
        Map<String, Object> params = new HashMap<>();
        params.put("type", "jsapi");
        params.put("access_token", token);
        return getWechatReturn(params, url);
    }

//--------------------------------------刷新调用微信接口的票据ticket结束---------------------------------------------------

//--------------------------------------生成调用微信js-sdk所需要的参数开始---------------------------------------------------

    /**
     * 生成调用微信js-sdk的签名
     *
     * @param url
     * @return
     */
    public ShareParam sign(String url) {
        ShareParam shareParam = new ShareParam();

        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + WeChatUtils.TICKET +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        shareParam.setJsapi_ticket(WeChatUtils.TICKET);
        shareParam.setNonceStr(nonce_str);
        shareParam.setTimestamp(timestamp);
        shareParam.setSignature(signature);
        shareParam.setAppId(appId);
        return shareParam;
    }

//-----------------------------------------------微信支付开始------------------------------------------------------------------

    /**
     * 获取微信支付请求包
     */
    public WechatPayPackage getWechatPayPackage(String openId, String body, String attach, String outTradeNo, String
            totalFee, String tradeType, String notifyUrl) {
        WechatPayApply wechatPayApply = new WechatPayApply();
        wechatPayApply.setOpenId(openId);
        wechatPayApply.setBody(body);
        wechatPayApply.setAttch(attach);
        wechatPayApply.setOutTradeNo(outTradeNo);
        wechatPayApply.setTotalFee(totalFee);
        wechatPayApply.setTradeType(tradeType);
        wechatPayApply.setNotifyUrl(notifyUrl);
        wechatPayApply.setAppId(appId);
        wechatPayApply.setMchId(mchId);
        wechatPayApply.setNonceStr(getNonceStr());
        wechatPayApply.setSpbillCreateIp(spbillCreateIp);
        wechatPayApply.setSign(getWeixinPaySign(wechatPayApply));

        String xml = "<xml>"
                + "<appid>" + wechatPayApply.getAppId() + "</appid>"
                + "<mch_id>" + wechatPayApply.getMchId() + "</mch_id>"
                + "<nonce_str>" + wechatPayApply.getNonceStr() + "</nonce_str>"
                + "<sign>" + wechatPayApply.getSign() + "</sign>"
                + "<body><![CDATA[" + wechatPayApply.getBody() + "]]></body>"
                + "<out_trade_no>" + wechatPayApply.getOutTradeNo() + "</out_trade_no>"
                + "<attach>" + wechatPayApply.getAttch() + "</attach>"
                + "<total_fee>" + wechatPayApply.getTotalFee() + "</total_fee>"
                + "<spbill_create_ip>" + wechatPayApply.getSpbillCreateIp() + "</spbill_create_ip>"
                + "<notify_url>" + wechatPayApply.getNotifyUrl() + "</notify_url>"
                + "<trade_type>" + wechatPayApply.getTradeType() + "</trade_type>"
                + "<openid>" + wechatPayApply.getOpenId() + "</openid>"
                + "</xml>";
        logger.info(xml);
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        Map<String, String> map = doPostMap(createOrderURL, xml);
        logger.info("------------------------生成预支付信息成功--------------------------------");
        logger.info(map.toString());
        String nonceStr = "";
        String packages = "";
        String signType = "MD5";
        String timestamp = "";
        String paySign = "";
        if (map != null && map.get("return_code").equals("SUCCESS") && map.get("result_code").equals("SUCCESS")) {
            nonceStr = map.get("nonce_str");
            packages = "prepay_id=" + map.get("prepay_id");
            signType = "MD5";
            timestamp = create_timestamp();

            SortedMap<String, String> sortedMap = new TreeMap<>();
            sortedMap.put("appId", appId);
            sortedMap.put("timeStamp", timestamp);
            sortedMap.put("nonceStr", nonceStr);
            sortedMap.put("package", packages);
            sortedMap.put("signType", signType);

            String stringA = mapMontageAsString(sortedMap);
            String stringSignTemp = stringA + "&key=" + mchKey;
            paySign = MD5Digest(stringSignTemp).toUpperCase();
        }

        WechatPayPackage weixinPayPackage = new WechatPayPackage();
        weixinPayPackage.setNonceStr(nonceStr);
        weixinPayPackage.setPackages(packages);
        weixinPayPackage.setSignType(signType);
        weixinPayPackage.setTimestamp(timestamp);
        weixinPayPackage.setPaySign(paySign);
        weixinPayPackage.setAppId(appId);
        weixinPayPackage.setNeedPay(true);
        return weixinPayPackage;
    }

    /**
     * 获取微信支付的签名
     *
     * @param weixinPayApply
     * @return
     */
    public String getWeixinPaySign(WechatPayApply weixinPayApply) {

        //将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序）
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("trade_type", weixinPayApply.getTradeType());
        packageParams.put("openid", weixinPayApply.getOpenId());
        packageParams.put("body", weixinPayApply.getBody());
        packageParams.put("attach", weixinPayApply.getAttch());
        packageParams.put("out_trade_no", weixinPayApply.getOutTradeNo());
        packageParams.put("total_fee", weixinPayApply.getTotalFee());
        packageParams.put("notify_url", weixinPayApply.getNotifyUrl());
        packageParams.put("spbill_create_ip", weixinPayApply.getSpbillCreateIp());
        packageParams.put("nonce_str", weixinPayApply.getNonceStr());
        packageParams.put("appid", weixinPayApply.getAppId());
        packageParams.put("mch_id", weixinPayApply.getMchId());

        //使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA
        String stringA = mapMontageAsString(packageParams);

        //在stringA最后拼接上key得到stringSignTemp字符串
        String stringSignTemp = stringA + "&key=" + mchKey;

        //对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写
        return MD5Digest(stringSignTemp).toUpperCase();
    }

    /**
     * 获取微信支付的签名
     *
     * @param weixinPayApply
     * @return
     */
    public String getWeixinPaySign(WechatPayRefund weixinPayApply) {

        //将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序）
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", weixinPayApply.getAppId());
        packageParams.put("mch_id", weixinPayApply.getMchId());
        packageParams.put("nonce_str", weixinPayApply.getNonceStr());
        packageParams.put("out_trade_no", weixinPayApply.getOutTradeNo());
        packageParams.put("out_refund_no", weixinPayApply.getOutRefundNo());
        packageParams.put("total_fee", weixinPayApply.getTotalFee().toString());
        packageParams.put("refund_fee", weixinPayApply.getRefundFee().toString());

        //使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA
        String stringA = mapMontageAsString(packageParams);

        //在stringA最后拼接上key得到stringSignTemp字符串
        String stringSignTemp = stringA + "&key=" + mchKey;

        //对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写
        return MD5Digest(stringSignTemp).toUpperCase();
    }

//-----------------------------------------------微信支付结束------------------------------------------------------------------

    //-----------------------------------------------微信退款开始---------------------------------------------------------------------

    /**
     * @param outTradeNo
     * @param refundFee
     * @Author: ffq
     * @Date: 2018/6/4 18:20
     * @Description:作用：申请退款<br> 场景：刷卡支付、公共号支付、扫码支付、APP支付
     */
    public Map<String, String> refund(String outTradeNo, Integer refundFee) throws Exception {
        //设置公共参数
        WechatPayRefund wechatPayRefund = new WechatPayRefund();
        wechatPayRefund.setAppId(appId);
        wechatPayRefund.setMchId(mchId);
        wechatPayRefund.setNonceStr(getNonceStr());
        wechatPayRefund.setOutRefundNo(outTradeNo);
        wechatPayRefund.setOutTradeNo(outTradeNo);
        wechatPayRefund.setRefundFee(refundFee);
        wechatPayRefund.setTotalFee(refundFee);
        //设置sign
        String weixinPaySign = getWeixinPaySign(wechatPayRefund);
        wechatPayRefund.setSign(weixinPaySign);
        logger.info("outTradeNo:{}退款", outTradeNo);
        return this.refund(wechatPayRefund, 5000, 5000);
    }

    /**
     * 作用：申请退款<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付<br>
     * 其他：需要证书
     *
     * @param wechatPayRefund  向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs    读超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> refund(WechatPayRefund wechatPayRefund, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
        String respXml = this.requestWithCert(url, wechatPayRefund, connectTimeoutMs, readTimeoutMs);
        logger.info("outTradeNo:{}退款成功,respXml:{}", wechatPayRefund.getOutTradeNo(), respXml);
        return this.xmlStr2Map(respXml);
    }

    /**
     * 需要证书的请求
     *
     * @param strUrl           String
     * @param reqData          向wxpay post的请求数据  Map
     * @param connectTimeoutMs 超时时间，单位是毫秒
     * @param readTimeoutMs    超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public String requestWithCert(String strUrl, WechatPayRefund reqData,
                                  int connectTimeoutMs, int readTimeoutMs) throws Exception {
        //获取随机字符串
        String msgUUID = reqData.getNonceStr();
        String reqBody = "<xml>\n" +
                "   <appid>" + reqData.getAppId() + "</appid>\n" +
                "   <mch_id>" + reqData.getMchId() + "</mch_id>\n" +
                "   <nonce_str>" + reqData.getNonceStr() + "</nonce_str> \n" +
                "   <out_refund_no>" + reqData.getOutRefundNo() + "</out_refund_no>\n" +
                "   <out_trade_no>" + reqData.getOutTradeNo() + "</out_trade_no>\n" +
                "   <refund_fee>" + reqData.getRefundFee() + "</refund_fee>\n" +
                "   <total_fee>" + reqData.getTotalFee() + "</total_fee>\n" +
                "   <transaction_id></transaction_id>\n" +
                "   <sign>" + reqData.getSign() + "</sign>\n" +
                "</xml>";
        String UTF8 = "UTF-8";
        URL httpUrl = new URL(strUrl);
        char[] password = reqData.getMchId().toCharArray();
        //读取证书位置
        InputStream certStream = getClass().getClassLoader().getResourceAsStream("cert/apiclient_cert.p12");
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(certStream, password);

        // 实例化密钥库 & 初始化密钥工厂
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password);

        // 创建SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(connectTimeoutMs);
        httpURLConnection.setReadTimeout(readTimeoutMs);
        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(reqBody.getBytes(UTF8));

        // if (httpURLConnection.getResponseCode()!= 200) {
        //     throw new Exception(String.format("HTTP response code is %d, not 200", httpURLConnection.getResponseCode()));
        // }

        //获取内容
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        final StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        String resp = stringBuffer.toString();
        if (stringBuffer != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        if (certStream != null) {
            try {
                certStream.close();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        // if (httpURLConnection!=null) {
        //     httpURLConnection.disconnect();
        // }
        return resp;
    }

//-----------------------------------------------微信退款结束---------------------------------------------------------------------

//-----------------------------------------调用微信接口工具-----------------------------------------------------

    /**
     * 调用微信接口，获得返回值,支持GET方法
     */
    public static Map<String, Object> getWechatReturn(Map<String, Object> params, String url) {
        try {
            logger.info("-----------------------params:{}", params);
            String jsonStr = HttpUtils.httpGetRequest(url, params);
            logger.info("-----------------------jsonStr is " + jsonStr + "---------------------");
            if (!StringUtils.isEmpty(jsonStr)) {
                return JsonUtils.json2object(jsonStr, Map.class, String.class, Object.class);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        throw new ValidationException(MessageCodes.WECHAT_AUTHORIZE_FAIL);
    }

    /**
     * 调用微信接口，支持post方法，参数与返回数据都为xml格式的字符串
     */
    private static Map<String, String> doPostMap(String url, String params) {
        Map<String, String> result = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            if (params != null && !params.equals("")) {
                httpPost.setEntity(new StringEntity(params, "UTF-8"));
            }
            HttpResponse res = httpClient.execute(httpPost);
            String responseContent = EntityUtils.toString(res.getEntity(), "UTF-8");  //响应内容
            result = xmlStr2Map(responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();  //关闭连接 ,释放资源
        }
        return result;
    }

    //-----------------------------------------调用微信接口工具-----------------------------------------------------

//----------------------------------------相关工具类开始--------------------------------------------------------------------

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 已经经过字典序排序的map拼接为一个字符串key1=value1&key2=value2&key3=value3
     */
    public static String mapMontageAsString(SortedMap<String, String> sortedMap) {
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<String, String>> es = sortedMap.entrySet();
        Iterator<Map.Entry<String, String>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String k = entry.getKey();
            String v = entry.getValue();
            if (v != null && !v.equals("")) {
                sb.append(k + "=" + v + "&");
            }
        }
        return sb.substring(0, sb.lastIndexOf("&"));
    }

    /**
     * MD5加密
     */
    public static String MD5Digest(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 将拼接而成的字符串进行MD5加密
            byte[] digest = md.digest(content.getBytes("utf-8"));
            //将加密后的字节数组转成十六进制的字符串
            return byteArrayToHexStr(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 字节数组转换为十六进制的字符串
     *
     * @param byteArray
     * @return
     */
    public static String byteArrayToHexStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 字节转换为十六进制的字符串
     *
     * @param mByte
     * @return
     */
    public static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     */
    public static Map<String, String> xmlStr2Map(String strxml) throws Exception {
        if (null == strxml || "".equals(strxml)) {
            return null;
        }

        Map<String, String> m = new HashMap<String, String>();
        InputStream in = String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    public static InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    /**
     * 生成随机字符串
     */
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取随机字符串
     *
     * @return
     */
    public static String getNonceStr() {
        // 随机数
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String currTime = outFormat.format(now);

        // 8位日期
        String strTime = currTime.substring(8, currTime.length());

        // 四位随机数
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < 4; i++) {
            num = num * 10;
        }
        String strRandom = (int) ((random * num)) + "";

        // 10位序列号,可以自行调整。
        return strTime + strRandom;
    }

    /**
     * 生成时间戳
     */
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 微信通知校验
     *
     * @param timestamp
     * @param nonce
     * @param signature
     * @return
     */
    public boolean validateSign(String timestamp, String nonce, String signature) {
        boolean isPass = false;
        //tuBxvcObGPeoRIho和公众号那边的服务器配置里的Token一致
        String[] ArrTmp = {"tuBxvcObGPeoRIho", timestamp, nonce};
        Arrays.sort(ArrTmp);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ArrTmp.length; i++) {
            sb.append(ArrTmp[i]);
        }
        String pwd = Encrypt(sb.toString());
        if (pwd.equals(signature)) {
            System.out.println("验证成功");
            isPass = true;
            return isPass;
        } else {
            System.out.println("验证失败");
            return isPass;
        }
    }

    private static String Encrypt(String strSrc) {
        MessageDigest md;
        String strDes;

        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(bt);
            strDes = bytes2Hex(md.digest()); //to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm.");
            return null;
        }
        return strDes;
    }

    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
