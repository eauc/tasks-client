(ns tasks.db
  (:require [cljs.spec :as spec]
            [re-frame.core :as re-frame]
            [tasks.debug :as debug :refer [debug?]]
            [tasks.specs.prompt :as prompt-spec]
            [tasks.specs.task]
            [tasks.specs.task-edit]
            [tasks.specs.tasks-list]
            [tasks.specs.tasks-list-edit]))

(spec/def ::page
  (spec/nilable keyword?))

(spec/def ::prompt
  (spec/nilable :tasks.specs.prompt/prompt))

(spec/def ::task-edit
  (spec/nilable :tasks.specs.task-edit/edit))

(spec/def ::tasks-filter string?)

(spec/def ::tasks-list-edit
  (spec/nilable :tasks.specs.tasks-list-edit/edit))

(spec/def ::tasks-show-details
  (spec/nilable :tasks.specs.task/id))

(spec/def ::db
  (spec/keys :req-un [::page
                      ::prompt
                      ::task-edit
                      ::tasks-filter
                      :tasks.specs.tasks-list/tasks-list
                      ::tasks-list-edit
                      :tasks.specs.tasks-list/tasks-lists-names
                      ::tasks-show-details]))

(defn check-db-schema [db]
  (when (not (spec/valid? ::db db))
    (throw (ex-info "db spec check failed"
                    (spec/explain-data ::db db)))))

(def check-spec-interceptor
  (re-frame/after check-db-schema))

(def default-interceptors
  [check-spec-interceptor
   (when debug? re-frame/debug)])

(def default-db
  {:page nil
   :prompt nil
   :task-edit nil
   :tasks-filter ""
   :tasks-list {:name "default"
                :id nil
                :tasks []}
   :tasks-list-edit nil
   :tasks-lists-names []
   :tasks-show-details nil})

(re-frame/reg-event-fx
 :initialize-db
 [check-spec-interceptor
  (re-frame/inject-cofx :storage-init)]
 (fn [{:keys [storage]} _]
   {:db (debug/spy "default-db" (merge default-db storage))}))
