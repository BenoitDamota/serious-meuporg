/**
 * Created by Morgane TROYSI on 19/05/17.
 */

(function() {
    'use strict';

    /** @ngInject */
    function ComponentProgressController(CommonProgressService, $scope, AuthenticationService, UserService, constant) {
        var ctrl = this;
        ctrl.CommonProgressService = CommonProgressService;

        ctrl.init = function() {
            ctrl.user = {};
            var id = AuthenticationService.getUserId();
            UserService.getUser(id).then(function(response) {
                ctrl.user = response;
                ctrl.totalXp = ctrl.user.level * constant.XP;
                CommonProgressService.setMoney(ctrl.user.money);
            });

            /**
             * Watch the money
             * Variable to watch : money
             */
            $scope.$watch('this.ctrl.CommonProgressService.money', function(newValues) {
                ctrl.user.money = newValues;
            });
        };

        ctrl.getXpPercent = function() {
            return (ctrl.user.experience * 100) / ctrl.totalXp;
        };

        ctrl.init();
    }


    angular.module('hello')
        .component('mgProgress', {
            controller: ComponentProgressController,
            controllerAs: 'ctrl',
            templateUrl: 'js/common/component/progress/progress.view.html'
        });
})();