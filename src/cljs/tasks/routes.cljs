(ns tasks.routes
  (:import goog.History)
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [tasks.debug :as debug]))

(defroute home "/" {}
  (debug/spy "route home")
  (re-frame/dispatch [:page :home]))

(defroute task-create "/task/create" {}
  (debug/spy "route create")
  (re-frame/dispatch [:task-create-start]))

(defroute task-edit "/task/edit/:id" [id]
  (debug/spy "route edit")
  (re-frame/dispatch [:task-edit-start id]))

(defroute tasks-list-create "/list/create" {}
  (debug/spy "route list-create")
  (re-frame/dispatch [:tasks-list-create-start]))

(defroute tasks-list-edit "/list/edit/:name" [name]
  (debug/spy "route list-edit")
  (re-frame/dispatch [:tasks-list-edit-start name]))

(defonce history (History.))

(re-frame/reg-fx
 :nav
 (fn [{:keys [route params]}]
   (let [url (subs (route params) 1)]
     (.setToken history url))))

(re-frame/reg-event-fx
 :nav
 (fn [_ [_ route params]]
   {:nav {:route route :params params}}))

(defn hook-browser-navigation! []
  (events/listen history EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (.setEnabled history true))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  (hook-browser-navigation!))
