package redisDemo;
/**
 * 
 * base64格式为java8util套件下
 */
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import redis.clients.jedis.Jedis;


public class Base64ImgUtils {

	/**
	 * 将网络图片进行base64位编码
	 */
	public static String encodeImageToBase64(URL imageUrl){
		ByteArrayOutputStream outputStream = null;
		try{
			BufferedImage bufferedImage = ImageIO.read(imageUrl);
			outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage,"jpg",outputStream);
		}catch(MalformedURLException el){
			el.printStackTrace();
		}catch(IOException ie){
			ie.printStackTrace();
		}
		//对字符串进行base64编码
		//Base64.Encoder UrlEncoder = Base64.getUrlEncoder();
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(outputStream.toByteArray());
	}
	
	/**
	 * 将网络图片进行base64解码，并保存到指定目录中去
	 */
	public static String decodeBase64ToImage(String base64,String path,String imgName){
		Base64.Decoder decoder = Base64.getDecoder();
		try{
			FileOutputStream write = new FileOutputStream(new File(path + imgName));
	        byte[] decoderBytes = decoder.decode(base64);
	        write.write(decoderBytes);
	        write.close();
		}catch(IOException ie){
			ie.printStackTrace();
		}
		return null;
	}
	
	   public static void main(String [] args){
	        URL url = null;
	        Jedis jedis = new Jedis("localhost");
	        jedis.flushDB();
        	System.out.println("服务正在运行: "+jedis.ping());
	        try {
	            url = new URL("https://gss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/9a504fc2d5628535aedbd09d95ef76c6a7ef6356.jpg");
	            String encoderStr = Base64imageUtils.encodeImgageToBase64(url);
	            String key ="pictures";
		        jedis.lpush(key, encoderStr);
		        //int start = 0;
		        //Long end = jedis.llen(key);
		        List<String> list = jedis.lrange(key,0,-1);
		        for(int i = 0 ; i < list.size();i++){
		        	System.out.println("列表项："+list.get(i));
		        }
		        System.out.println(jedis.ttl(key));
		        Base64imageUtils.decodeBase64ToImage(encoderStr, "E:/", "test01.jpg");
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        }
	    }
}
