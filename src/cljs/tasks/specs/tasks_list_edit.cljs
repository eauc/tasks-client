(ns tasks.specs.tasks-list-edit
  (:require [cljs.spec :as spec]
            [tasks.specs.tasks-list :as tasks-list-spec]))

(spec/def ::new-name string?)

(spec/def ::current-name
  (spec/nilable :tasks.specs.tasks-list/name))

(spec/def ::edit
  (spec/keys :req-un [::new-name
                      ::current-name]))
