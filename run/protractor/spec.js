const loginPageConfig = require('./loginPageConfig');

describe('Protractor Demo App', function() {
  it('should have a title', function() {
    browser.waitForAngularEnabled(false);
    browser.get('https://kiev-cpsg-1.luxoft.com/connect/PortalMain');

    browser.sleep(5000);
    var usernameInput = element(by.id('LoginUserPassword_auth_username'));
    var passwordInput = element(by.id('LoginUserPassword_auth_password'));
    var loginButton = element(by.id('UserCheck_Login_Button'));
    usernameInput.sendKeys(loginPageConfig.username || 'unspecified');
    passwordInput.sendKeys(loginPageConfig.password || 'unspecified');
    loginButton.click();

    browser.sleep(5000);
  });
});