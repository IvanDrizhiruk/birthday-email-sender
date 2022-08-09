// conf.js
exports.config = {
  framework: 'jasmine',
  directConnect: true, //Start protractor without start the selenium server using webdriver-manager start. default value is fales

  capabilities: {
    browserName: 'firefox',
    'moz:firefoxOptions': {
      args: ['--verbose']
      // ,
      // binary: 'C:/Program Files/Mozilla Firefox/firefox.exe' //Provide binary location to avoid potential binary not found errors
      // //Need to start cmd via admin mode to avoid permission error
    }
  },
  //set to true So each spec will be executed in own browser instance. default value is false
  //restartBrowserBetweenTests: true,
  jasmineNodeOpts: {
    //Jasmine provides only one timeout option  timeout in milliseconds don't add ;
    defaultTimeoutInterval: 180000
  },

  seleniumAddress: 'http://localhost:4444/wd/hub',
  specs: ['spec.js']
}