(ns tasks.specs.task-edit
  (:require [cljs.spec :as spec]
            [tasks.specs.task :as task-spec]))

(spec/def ::title string?)

(spec/def ::edit
  (spec/keys :req-un [:tasks.specs.task/id
                      ::title
                      :tasks.specs.task/body
                      :tasks.specs.task/done]))
