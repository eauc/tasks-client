(ns tasks.storage
  (:require [cljs.reader :as reader]
            [clojure.string :as string]
            [re-frame.core :as re-frame]
            ;; [clairvoyant.core :refer-macros [trace-forms]]
            ;; [re-frame-tracer.core :refer [tracer]]

            [tasks.debug :as debug]))

;; (trace-forms {:tracer (tracer :color "brown")}

(def storage-current-key "tasks_current")
(def storage-key-prefix  "tasks.")
(def storage-key-reg   #"^tasks\.")

(defn current-list->storage [current-list]
  (.setItem js/localStorage
            storage-current-key
            current-list))

(defn tasks->storage [list tasks]
  (.setItem js/localStorage
            (str storage-key-prefix list)
            (str tasks)))

(defn storage->lists []
  (->> (.keys js/Object js/localStorage)
       (js->clj)
       (filter #(re-find storage-key-reg %))
       (map #(string/replace % storage-key-reg ""))
       (sort)
       (into [])))

(defn storage->current-list []
  (or (.getItem js/localStorage storage-current-key)
      "default"))

(defn storage->tasks [list]
  (into [] (some->> (.getItem js/localStorage (str storage-key-prefix list))
                    (reader/read-string))))

(defn storage-init [coeffects _]
  (let [current-list (debug/spy "storage->current-list" (storage->current-list))
        lists (debug/spy "storage->lists" (storage->lists))
        tasks (debug/spy "storage->tasks" (storage->tasks current-list))]
    (assoc coeffects :storage {:current-list current-list
                               :lists lists
                               :tasks tasks})))

(defn storage-list [{:keys [event] :as coeffects} _]
  (let [[_ list] event
        tasks (debug/spy "storage->tasks" (storage->tasks list))]
    (assoc coeffects :storage {:current-list list
                               :tasks tasks})))

(re-frame/reg-cofx
  :storage-init
  storage-init)

(re-frame/reg-cofx
  :storage-list
  storage-list)

(re-frame/reg-sub
  :storage
  :<- [:tasks]
  :<- [:current-list]
  (fn storage-store [[tasks current-list] _]
    (current-list->storage current-list)
    (tasks->storage current-list tasks)))

;; )
