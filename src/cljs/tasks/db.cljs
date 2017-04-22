(ns tasks.db
  (:require [cljs.spec :as spec]
            [cljs.spec.impl.gen :as gen]
            [clojure.test.check.generators]
            [re-frame.core :as re-frame]
            [tasks.debug :as debug]))

(def test-tasks
  (->> (gen/sample (spec/gen :tasks.models.task/task) 1000)
       (map-indexed #(assoc %2 :id (str %1)))))

(def default-db
  {:tasks (into [] test-tasks)
   :edit nil
   :filter ""
   :page nil
   :show-details nil})

(re-frame/reg-event-db
 :initialize-db
 (fn [_ _] (debug/spy "default-db" default-db)))
