package testCases;

import org.testng.Assert;

import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.Baseclass;

public class TC_AccountRegistrationtTest extends Baseclass {
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registration() throws InterruptedException
	{
		logger.info("*********Starting TC_AccountRegistrationtTest************");
		logger.debug("This is Debug log message");
		try {
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on my Account Link");

		hp.clickRegister();
		logger.info("Clicked on my Register Link");

		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		logger.info("Providing customer details");

		regpage.setFirstname(randomString().toUpperCase());
		regpage.setLastname(randomString().toUpperCase());
		regpage.setemail(randomString()+"@gmail.com");
		regpage.setTelephone(randomNumber());
		
		String password=randomAlphaNumeric();
		regpage.setpassword(password);
		regpage.setconfirm(password);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		logger.info("Validating expected message.");
		
		String confmsg=regpage.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test failed");
			logger.debug("Debug logs....");
			
			Assert.assertTrue(false);
		}
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!!!");
		Thread.sleep(5000);
		}
		catch(Exception e)
		{
			Assert.fail();
			
		}
		logger.info("*******Finished TC_AccountRegistrationtTest*******");

		
		
		
		
	}
	}
