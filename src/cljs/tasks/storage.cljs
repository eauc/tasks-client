(ns tasks.storage
  (:require [cljs.reader :as reader]
            [re-frame.core :as re-frame]
            ;; [clairvoyant.core :refer-macros [trace-forms]]
            ;; [re-frame-tracer.core :refer [tracer]]
            ))

;; (trace-forms {:tracer (tracer :color "brown")}

(def storage-key "tasks.default")

(defn tasks->storage [tasks]
  (.setItem js/localStorage storage-key (str tasks)))

(defn storage->tasks []
  (into [] (some->> (.getItem js/localStorage storage-key)
                    (reader/read-string))))

(re-frame/reg-cofx
 :storage
 (fn [coeffects _]
   (let [tasks (storage->tasks)]
     (assoc coeffects :storage tasks))))

(re-frame/reg-sub
 :storage
 :<- [:tasks]
 (fn storage [tasks _]
   (tasks->storage tasks)))

;; )
