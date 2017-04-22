(ns tasks.components.page.handler
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-event-db
 :page
 (fn [db [_ page]] (assoc-in db [:page] page)))

;; (re-frame/reg-event-db
;;  :start-create
;;  (fn [db _] (-> db
;;                 (assoc-in [:page] :edit)
;;                 (assoc-in [:edit] {:id (str (.now js/Date))
;;                                    :title ""
;;                                    :body ""
;;                                    :done false}))))
