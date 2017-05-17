(function() {
    'use strict';

    var helloApp = angular.module('hello');

    /** @ngInject */
    helloApp.controller('LoginController', function($rootScope, LoginService) {
        var loginCtrl = this;

        loginCtrl.init = function() { /** constructeur (pseudo objet) */
            loginCtrl.credentials = [];
        };

        loginCtrl.loginAction = function() {
            var user = {
                email: loginCtrl.credentials.email,
                password: loginCtrl.credentials.password,
            };
            LoginService.authentification(user).then(function(data) { /** appel aux methodes du services */

                /* if ($rootScope.currentUser) {
          $location.path("/");
          loginCtrl.error = false;
        } else {
          $location.path("/login");
          loginCtrl.error = true;
            }*/
            });
        };

        loginCtrl.init();
    })
})();