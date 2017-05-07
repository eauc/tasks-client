(ns tasks.models.tasks-list
  (:require [cljs.spec :as spec]
            [clojure.string :as str]
            [tasks.specs.tasks-list]))

(spec/fdef create
           :args (spec/cat :lists :tasks.specs.tasks-list/tasks-lists-names
                           :name :tasks.specs.tasks-list/name)
           :ret :tasks.specs.tasks-list/tasks-lists-names
           :fn #(= (-> % (:ret) count) (-> % (:args) (:lists) count inc)))
(defn create
  "Add new tasks list"
  [lists list]
  (into [] (sort (cons list lists))))

(spec/fdef delete
           :args (spec/cat :lists :tasks.specs.tasks-list/tasks-lists-names
                           :name :tasks.specs.tasks-list/name)
           :ret :tasks.specs.tasks-list/tasks-lists-names
           :fn #(<= (-> % (:ret) count) (-> % (:args) (:lists) count)))
(defn delete
  "Remove tasks list"
  [lists list]
  (into [] (sort (remove #(= list %) lists))))

(spec/fdef rename
           :args (spec/cat :lists :tasks.specs.tasks-list/tasks-lists-names
                           :old-name :tasks.specs.tasks-list/name
                           :new-name :tasks.specs.tasks-list/name)
           :ret :tasks.specs.tasks-list/tasks-lists-names
           :fn #(>= (-> % (:ret) count) (-> % (:args) (:lists) count)))
(defn rename
  "Rename (or create new) tasks list"
  [lists old-name new-name]
  (->> lists
       (cons new-name)
       (remove #(= old-name %))
       (sort)
       (into [])))
