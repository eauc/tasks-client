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

(defroute create "/create" {}
  (debug/spy "route create")
  (re-frame/dispatch [:create-start]))

(defroute edit "/edit/:id" [id]
  (debug/spy "route edit")
  (re-frame/dispatch [:edit-start id]))

(defonce history (History.))

(defn nav!
  ([page] (.setToken history (subs (page) 1)))
  ([page params] (.setToken history (subs (page params) 1))))

(defn hook-browser-navigation! []
  (events/listen history EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (.setEnabled history true))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  (hook-browser-navigation!))
