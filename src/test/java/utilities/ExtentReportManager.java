package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.Baseclass;

public class ExtentReportManager implements ITestListener{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext testContext)
	{
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName="Test-Report-"+timeStamp +".html";
		sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName);
		
		sparkReporter.config().setDocumentTitle("Opencart Automation Report");//Title of report
		sparkReporter.config().setReportName("Opencart Functional Testing");// name of report
		sparkReporter.config().setTheme(Theme.DARK);
		
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub-Module", "Customers");
		extent.setSystemInfo("UserName", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os= testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		
		String browser=testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
		
		}
		public void onTestSuccess(ITestResult result)
		{
			test=extent.createTest(result.getTestClass().getName());
			test.assignCategory(result.getMethod().getGroups());//to display groups in report
			test.log(Status.PASS, result.getName()+"got successfuly excecuted");
	}
	
		public void onTestFailure(ITestResult result)
		{
			test=extent.createTest(result.getTestClass().getName());
			test.assignCategory(result.getMethod().getGroups());//to display groups in report
			test.log(Status.FAIL,result.getName()+ "got failed");
			test.log(Status.INFO, result.getThrowable().getMessage());
			String imgPath=new Baseclass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		}
		public void onTestSkipped(ITestResult result)
		{
			test=extent.createTest(result.getTestClass().getName());
			test.assignCategory(result.getMethod().getGroups());//to display groups in report

			test.log(Status.SKIP,result.getName()+ "got skipped");
			test.log(Status.INFO, result.getThrowable().getMessage());
		}
		
		public void onFinish(ITestContext context)
		{
			extent.flush();
			String pathofExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
			File extentReport=new File(pathofExtentReport);
			try {
				Desktop.getDesktop().browse(extentReport.toURI());
			}catch(IOException e) {
				e.printStackTrace();
			}
		
		/*try {
			
			URL url=new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
			//create email message
			ImageHtmlEmail email=new ImageHtmlEmail();
			//email.setDataSourceResolver(new DataSourceResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("pitchuka.surekha17@gmail.com","password"));
			email.setSSLOnConnect(true);
			email.setFrom("pitchuka.surekha17@gmail.com");
			email.setSubject("Test Results");
			email.setMsg("Please find Attached Report");
			email.addTo("surekha.pitchuka@gmail.com");
			email.attach(url, "extent report", "please check report");
			email.send();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			
		}*/

}
}