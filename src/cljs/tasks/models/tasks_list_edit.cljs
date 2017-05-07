(ns tasks.models.tasks-list-edit
  (:require [cljs.spec :as spec]
            [tasks.specs.tasks-list]
            [tasks.specs.tasks-list-edit]))

(spec/fdef describe-errors
           :args (spec/cat :edit :tasks.specs.tasks-list-edit/edit
                           :lists-names :tasks.specs.tasks-list/tasks-lists-names)
           :ret map?)
(defn describe-errors
  "Return a map of validation errors message for a list edit"
  [edit lists-names]
  (let [new-name (:new-name edit)
        changed (not= new-name (:current-name edit))]
    (cond
      (not (spec/valid? :tasks.specs.tasks-list/name (:new-name edit)))
      {:new-name "Name should be a non-empty string"}
      (and changed (some #{new-name} lists-names))
      {:new-name "A tasks list with this name already exists"}
      :else
      {})))
