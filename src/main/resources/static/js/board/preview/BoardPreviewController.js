(function() {
    'use strict';

    /** @ngInject */
    angular.module('hello')
        .controller('BoardPreviewController', function($state, $stateParams, BoardService, $timeout) {
            var ctrl = this;

            /**
             * Constructor
             */
            ctrl.init = function() {
                ctrl.openPanelFilter = false;
                ctrl.openPanelNewColonne = false;
            };

            /**
             * Open panel FILTER
             */
            ctrl.openPanelFilterAction = function(element) {
                $(element).slideToggle(500);
            }

            /**
             * Open panel Task (Kanban)
             */
            ctrl.zoomTask = function(type) {
                $($('.boxMatrice .panelMatrice').get().reverse()).each(function(index) {
                    $(this).fadeToggle(150);
                });
                $timeout(function() { $('.bigPanelMatrice').fadeToggle(); }, 200);
                ctrl.sizeKanban();
            }

            ctrl.sizeKanban = function() {
                var width = 0;
                $('.contentKanban .columnKanban').each(function() {
                    console.log($(this).width());
                    width += $(this).width() + 51;
                });
                $('.contentKanban').width(width);
            }

            ctrl.init();
        });
})();