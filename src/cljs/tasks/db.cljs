(ns tasks.db
  (:require [cljs.spec :as spec]
            ;; [cljs.spec.impl.gen :as gen]
            ;; [clojure.test.check.generators]
            [re-frame.core :as re-frame]
            [tasks.debug :as debug]))

(spec/def ::current-list (spec/and (complement empty?)
                                   string?))

(spec/def ::lists (spec/* ::current-list))

(spec/def ::title string?)

(spec/def ::edit-task
  (spec/keys :req-un [:tasks.models.task/id
                      ::title
                      :tasks.models.task/body
                      :tasks.models.task/done]))

(spec/def ::edit
  (spec/nilable ::edit-task))

(spec/def ::filter string?)

(spec/def ::page
  (spec/nilable keyword?))

(spec/def ::show-details
  (spec/nilable :tasks.models.task/id))

(spec/def ::tasks
  :tasks.models.tasks/tasks)

(spec/def ::db
  (spec/keys :req-un [::current-list
                      ::edit
                      ::filter
                      ::lists
                      ::page
                      ::show-details
                      ::tasks]))

(defn check-db-schema [db]
  (when (not (spec/valid? ::db db))
    (throw (ex-info "db spec check failed"
                    (spec/explain-data ::db db)))))

(def check-spec-interceptor
  (re-frame/after check-db-schema))

;; (def test-tasks
;;   (->> (gen/sample (spec/gen :tasks.models.task/task))
;;        (map-indexed #(assoc %2 :id (str %1)))))

(def default-db
  { ;; :tasks (into [] test-tasks)
   :tasks []
   :current-list nil
   :edit nil
   :filter ""
   :lists []
   :page nil
   :show-details nil})

(re-frame/reg-event-fx
 :initialize-db
 [check-spec-interceptor
  (re-frame/inject-cofx :storage-init)]
 (fn [{:keys [storage]} _]
   {:db (debug/spy "default-db" (merge default-db storage))}))
