(ns tasks.specs.auth
  (:require [cljs.spec :as spec]
            [clojure.string :as str]))

(spec/def ::status
  #{"Creating..." "Syncing..." "Saving..."})

(spec/def ::token
  (spec/and string?
            (comp not empty? str/trim)))

(spec/def ::auth
  (spec/keys :opt-un [::token ::status]))
