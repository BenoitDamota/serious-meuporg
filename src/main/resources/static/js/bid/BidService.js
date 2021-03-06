(function() {
    'use strict';

    var helloApp = angular.module('hello');

    /** @ngInject */
    helloApp.service('BidService', function(BidWS) {
        var svc = {};

        /**
         * Retourne la liste des enchère d'un utilisateur pour un tableau
         * @param boardId id du tableau
         * @param userId id de l'utilisateur
         */
        svc.getListBidByBoardAndUser = function(boardId, userId) {
            return BidWS.getListBidByBoardAndUser(boardId, userId).then(function(response) {
                return response.data;
            });
        };

        /**
         * Affecte un temps à une tâche aux enchères
         * @param taskId id de la tâcheUser
         * @param duration durée
         */
        svc.addOrUpdateTaskUserBid = function(taskUserId, duration) {
            return BidWS.addOrUpdateTaskUserBid(taskUserId, duration).then(function(response) {
                return response.data;
            });
        };

        /**
         * Retourne la liste des tâches aux enchères qui sont finies
         * @param boardId id du tableau
         */
        svc.getListBidEndByBoard = function(boardId) {
            return BidWS.getListBidEndByBoard(boardId).then(function(response) {
                var list = {};
                if (response.data) {
                    response.data.forEach(function(element) {
                        if (!("taskUser_" + element.taskUser.id in list)) {
                            list["taskUser_" + element.taskUser.id] = { taskUser: element.taskUser, value: element.taskUser.duration, listUser: [] };
                        }
                        list["taskUser_" + element.taskUser.id].listUser.push({
                            "userId": element.user.id,
                            "userName": element.user.lastName + ' ' + element.user.firstName,
                            "duration": element.duration,
                            "etat": false,
                            "value": 0
                        });
                    });
                }
                return list;
            });
        };

        /**
         * Ajoute une ou plusieurs tâches aux enchères
         * @param date date de fin de l'enchère
         * @param listTaskUserId liste des id des tâches à metre en enchère
         */
        svc.addBid = function(date, listTaskUserId) {
            return BidWS.addBid(date, listTaskUserId).then(function(response) {
                return response.data;
            });
        };

        /**
         * Valide les encheres d'un tableau
         * @param boardId id du tableau
         * @param json liste de BidDTO
         */
        svc.validBid = function(boardId, json) {
            return BidWS.validBid(boardId, json).then(function(response) {
                return response.data;
            });
        };

        return svc;
    })
})();