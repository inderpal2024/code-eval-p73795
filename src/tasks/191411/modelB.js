// Combined JavaScript Script with Karma configuration file

// Required Imports
const path = require('path');

// Main function to test core functionality
function main() {
  console.log("Starting Karma Configuration...");

  // Karma configuration function
  function karmaConfig(config) {
    config.set({
      basePath: '',
      frameworks: ['jasmine', '@angular-devkit/build-angular'],
      plugins: [
        require('karma-jasmine'),
        require('karma-chrome-launcher'),
        require('karma-jasmine-html-reporter'),
        require('karma-coverage'),
        require('@angular-devkit/build-angular/plugins/karma')
      ],
      client: {
        jasmine: {
          // You can add configuration options for Jasmine here
          // The possible options are listed at https://jasmine.github.io/api/edge/Configuration.html
          // For example, you can disable the random execution with `random: false`
          // or set a specific seed with `seed: 4321`
          stopOnSpecFailure: true,
        },
        clearContext: false // Leave Jasmine Spec Runner output visible in browser
      },
      jasmineHtmlReporter: {
        suppressAll: true // Removes the duplicated traces
      },
      coverageReporter: {
        dir: path.join(__dirname, './coverage/final-project'),
        subdir: '.',
        reporters: [
          { type: 'html' },
          { type: 'text-summary' }
        ]
      },
      reporters: ['progress', 'kjhtml'],
      port: 9876,
      colors: true,
      logLevel: config.LOG_INFO,
      autoWatch: true,
      browsers: ['Chrome'],
      singleRun: false,
      restartOnFileChange: true
    });
  }

  // Example of invoking the karmaConfig function with a mock configuration object
  // To be replaced with a real Karma server instantiation when deployed
  const mockConfig = {
    set: (config) => console.log('Karma configuration set:', config),
    LOG_INFO: 'info'
  };

  karmaConfig(mockConfig);
}

// Execute main function
main();
