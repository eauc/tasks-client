(ns tasks.models.task-edit
  (:require [cljs.spec :as spec]
            [tasks.specs.task-edit]))

(def edit-error-messages
  {:title "Title should be a non-empty string"
   :body "Body should be a string"})

(spec/fdef describe-errors
           :args (spec/cat :edit :tasks.specs.task-edit/edit)
           :ret map?)
(defn describe-errors
  "Return a map of validation errors message for a task edit"
  [edit]
  (->> edit
       (spec/explain-data :tasks.specs.task/task)
       (:cljs.spec/problems)
       (map :path)
       (map (fn [path] [path (get-in edit-error-messages path)]))
       (reduce (fn [memo [path message]] (assoc-in memo path message)) {})))
