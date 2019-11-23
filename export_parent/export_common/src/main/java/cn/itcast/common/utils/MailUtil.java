package cn.itcast.common.utils;


import cn.itcast.domain.Msg;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailUtil {

	//实现邮件发送的方法
	public static void sendMsg(String to ,String subject ,String content) throws Exception{
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.qq.com ");  //设置主机地址   smtp.qq.com    smtp.sina.com

		props.setProperty("mail.smtp.auth", "true");//认证

		//2.产生一个用于邮件发送的Session对象
		Session session = Session.getInstance(props);

		//3.产生一个邮件的消息对象
		MimeMessage message = new MimeMessage(session);

		//4.设置消息的发送者
		Address fromAddr = new InternetAddress("2298283312@qq.com");
		message.setFrom(fromAddr);

		//5.设置消息的接收者
		Address toAddr = new InternetAddress(to);
		//TO 直接发送  CC抄送    BCC密送
		message.setRecipient(MimeMessage.RecipientType.TO, toAddr);

		//6.设置主题
		message.setSubject(subject);
		//7.设置正文
		message.setText(content);

		//8.准备发送，得到火箭
		Transport transport = session.getTransport("smtp");
		//9.设置火箭的发射目标
		transport.connect("smtp.qq.com", "2298283312@qq.com", "rjyjmqbcqnpneabj");
		//10.发送
		transport.sendMessage(message, message.getAllRecipients());

		//11.关闭
		transport.close();
	}

	public static List<Msg> getMsg() throws Exception {
		Properties  props = new Properties();
		List<Msg> list = new ArrayList<>();

		props.put("mail.transport.protocol", "smtp");//指定邮件发送协议  只接受邮件是可以不要写的
		props.put("mail.store.protocol", "imap");    //指定邮件接收协议
		props.put("mail.smtp.class", "com.sun.mail.smtp.SMTPTransport");//指定支持SMTP协议的Transport具体类，允许由第三方提供
		props.put("mail.imap.class", "com.sun.mail.imap.IMAPStore");    //指定支持IMAP协议的Store具体类，允许由第三方提供
		//以上这4个可以全部写上
		props.put("mail.smtp.host",  "pop.qq.com");//指定采用SMTP协议的邮件发送服务器的IP地址或主机名

		//注意你的服务器的地址
		//网易一共有这三个地址   pop.163.com  imap.163.com  stmp.163.com

		Session session =Session .getInstance(props);// 设置环境信息
		Store store = session.getStore("pop3");//指定接收邮件协议

		store.connect("pop.qq.com","1367280171@qq.com", "cghcyeosnknkhagb");
		//            网易邮箱的服务器地址   账号  密码

		//获得名为默认"inbox"的邮件夹当你自己有定义其他的邮件夹也可以写上去
		Folder folder=store.getFolder("inbox");

		//打开邮件夹
		folder.open(Folder.READ_ONLY);//它是一个邮件文件夹类。Folder类有两个常见的属性，READ_ONLY表示只读，READ_WRITE表示其内容可读可写

		//获得邮件夹中的邮件数目
	//	System.out.println("你一共有:"+folder.getMessageCount()+" 封邮件");

		//获得邮件夹中的未读邮件数目

	//	System.out.println("你一共有:"+folder.getUnreadMessageCount()+" 未读邮件");
	//	System.out.println("你一共删除了 "+folder.getDeletedMessageCount()+" 封邮件");
		for(int i=1;i<=folder.getMessageCount();i++){

			Msg msgEmail = new Msg();
			Message msg=folder.getMessage(i);
	//		System.out.println("========================================");
			//获得邮件的发送者、主题和正文
			if(msg.getFrom()[0].toString().contains("<")){
	//			System.out.println("邮件来自:"+msg.getFrom()[0].toString().substring(msg.getFrom()[0].toString().lastIndexOf("<")+1, msg.getFrom()[0].toString().lastIndexOf(">")));
				msgEmail.setFrom(msg.getFrom()[0].toString().substring(msg.getFrom()[0].toString().lastIndexOf("<")+1, msg.getFrom()[0].toString().lastIndexOf(">")));
			}else{
	 //			System.out.println("邮件来自:"+msg.getFrom()[0]);
				msgEmail.setFrom(msg.getFrom()[0].toString());
			}
		//	System.out.println("邮件主题:" + msg.getSubject());
			msgEmail.setTheme(msg.getSubject());
		//	System.out.println("发送日期:" + msg.getSentDate());
			msgEmail.setSentDate(msg.getSentDate());
			String type=msg.getContentType().toString().substring(0, msg.getContentType().toString().indexOf(";"));
		//	System.out.println("邮件类型:"+type);
		//	System.out.println("邮件内容:"+msg.getContentType().toString());//multipart  当文件类型为multipart/* 时不能正确显示

			if(type.equals("text/plain")) {
				//请你根据文件的类型来更改文件的解析方式  text/html  multipart/alternative表示复合类型
			//	System.out.println((String) msg.getContent());
				msgEmail.setContent((String) msg.getContent());
			}
			InternetAddress[] address = (InternetAddress[]) msg.getFrom();
			String from = address[0].getAddress();//这个是发邮件人的地址
			if (from == null) {
				from = "";
			}
			String personal = address[0].getPersonal();//这个是发邮件的人的姓名
			if (personal == null) {
				personal = "";
			}
			String fromaddr = personal + "<" + from + ">";
		//	System.out.println("邮件作者："+fromaddr);
		//	System.out.println("========================================\r\n");

			list.add(msgEmail);
		}
		return list;
	}
}
