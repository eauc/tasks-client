(ns tasks.db
  (:require [cljs.spec :as spec]
            [cljs.spec.impl.gen :as gen]
            [clojure.test.check.generators]
            [re-frame.core :as re-frame]))

(def default-db
  {:tasks (into [] (gen/sample (spec/gen :tasks.models.task/task)))
   :edit nil
   :filter ""
   :page nil
   :show-details nil})

(re-frame/reg-event-db
 :initialize-db
 (fn [_ _] default-db))
