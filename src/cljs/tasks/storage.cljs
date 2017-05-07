(ns tasks.storage
  (:require [cljs.reader :as reader]
            [clojure.string :as string]
            [re-frame.core :as re-frame]
            [tasks.debug :as debug]))

(def storage-current-key "tasks_current")
(def storage-key-prefix  "tasks.")
(def storage-key-reg   #"^tasks\.")

(defn current-list-name->storage [list-name]
  (.setItem js/localStorage
            storage-current-key
            list-name))

(defn tasks-list->storage [tasks-list]
  (.setItem js/localStorage
            (str storage-key-prefix (:name tasks-list))
            (str tasks-list)))

(defn storage->lists-names []
  (->> (.keys js/Object js/localStorage)
       (js->clj)
       (filter #(re-find storage-key-reg %))
       (map #(string/replace % storage-key-reg ""))
       (sort)
       (into [])))

(defn storage->current-list-name []
  (or (.getItem js/localStorage storage-current-key)
      "default"))

(defn storage->tasks-list [list-name]
  (let [tasks-list (some->> (.getItem js/localStorage (str storage-key-prefix list-name))
                            (reader/read-string))]
    (if (vector? tasks-list)
      {:name list-name
       :id nil
       :tasks tasks-list}
      (or tasks-list
          {:name list-name
           :id nil
           :tasks []}))))

(defn storage-init [coeffects _]
  (let [current-list (debug/spy "storage->current-list-name" (storage->current-list-name))
        lists-names (debug/spy "storage->lists-names" (storage->lists-names))
        tasks-list (debug/spy "storage->tasks-list" (storage->tasks-list current-list))]
    (assoc coeffects :storage {:tasks-lists-names lists-names
                               :tasks-list tasks-list})))

(defn storage-list [{:keys [event] :as coeffects} default-list-name]
  (let [[_ event-list-name] event
        list-name (or default-list-name event-list-name)
        tasks-list (debug/spy "storage->tasks-list" (storage->tasks-list list-name))]
    (assoc coeffects :storage {:tasks-list tasks-list})))

(re-frame/reg-cofx
 :storage-init
 storage-init)

(re-frame/reg-cofx
 :storage-list
 storage-list)

(re-frame/reg-fx
 :storage-delete
 (fn [id]
   (.removeItem js/localStorage
                (str storage-key-prefix id))))

(re-frame/reg-sub
 :storage
 :<- [:tasks-list]
 (fn storage-store [tasks-list _]
   (current-list-name->storage (:name tasks-list))
   (tasks-list->storage tasks-list)))
