(ns tasks.specs.tasks-list
  (:require [cljs.spec :as spec]
            [tasks.specs.task :as task-spec]
            [clojure.string :as str]))

(spec/def ::id
  (spec/nilable
   (spec/and string?
             (comp not empty? str/trim))))

(spec/def ::name
  (spec/and string?
            (comp not empty? str/trim)))

(spec/def ::updatedAt
  (spec/and string?
            (comp not empty? str/trim)))

(spec/def ::user
  (spec/and string?
            (comp not empty? str/trim)))

(spec/def ::tasks-list
  (spec/keys :req-un [::id ::name :task-spec/tasks]
             :opt-un [::updatedAt ::user]))

(spec/def ::tasks-lists-names
  (spec/coll-of ::name))
