/**
 * Created by Morgane TROYSI on 22/05/17.
 */

(function() {
    'use strict';

    /** @ngInject */
    angular.module('hello')
        .controller('InventoryController', function($scope, AuthenticationService, InventoryService, CommonDialogService, CommonNotificationBoxService, CommonItemService) {
            var ctrl = this;
            ctrl.inventory = [];

            ctrl.init = function() {
                ctrl.filter = { type: "TOUT" };
                ctrl.getListItem();
                $scope.$watch('this.ctrl.filter.type', function() {
                    ctrl.filterItems();
                });
            };


            /**
             * Retourne la liste des objets de l'inventaire
             */
            ctrl.getListItem = function() {
                InventoryService.getInventory(Number(AuthenticationService.getUserId())).then(function(data) {
                    ctrl.inventory = data;
                    ctrl.filteredInventory = data;
                    CommonItemService.setItems(angular.copy(data));
                });
            };

            /**
             * Open panel FILTER
             */
            ctrl.openPanelFilterAction = function(element) {
                $(element).slideToggle(500);
            };

            /**
             * Filtre les items selon le type de filtre sélectionné
             */
            ctrl.filterItems = function() {
                ctrl.filteredInventory = ctrl.inventory.filter(function(e) {
                    return e.type === ctrl.filter.type || ctrl.filter.type === 'TOUT';
                });
            };

            /**
             * Retire un objet de l'inventaire
             * @param item l'objet à retirer
             */
            ctrl.removeFromInventory = function(item) {
                CommonDialogService.confirmation('Êtes-vous sûr de vouloir jeter ' + item.name + ' ? Cette action est irréversible.', function() {
                    // Suppression d'un item
                    ctrl.removeItem(item);

                }, null, 'modalThrowItem', "Jeter un objet", "Valider", "Annuler");
            };

            /**
             * Active ou désactive un item
             * @param idItem id de l'item
             * @param active {boolean}
             */
            ctrl.activeElement = function(idItem, active) {
                InventoryService.activeItem(idItem, !active).then(function() {
                    ctrl.getListItem();
                });
            };

            /**
             * Retire un objet de l'inventaire
             * @param item id de l'objet à retirer
             */
            ctrl.removeItem = function(item) {
                InventoryService.removeItem(item.id).then(function() {
                    CommonNotificationBoxService.info("L'objet a été jeté", "");

                    ctrl.inventory = _.reject(ctrl.inventory, function(e) {
                        return e.id === item.id;
                    });
                    ctrl.filteredInventory = angular.copy(ctrl.inventory);
                    ctrl.filterItems();
                });
            };

            ctrl.init();
        })
})();