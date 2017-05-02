(ns tasks.components.prompt.db
  (:require [cljs.spec :as spec]
            [clojure.string :as str]))

(spec/def ::type #{:alert :confirm :prompt})

(spec/def ::message (spec/and string?
                              (comp not empty? str/trim)))

(spec/def ::on-cancel (spec/coll-of keyword? :kind vector?))

(spec/def ::on-validate (spec/coll-of keyword? :kind vector?))

(spec/def ::value (spec/or :string-value string?
                           :number-value number?))

(spec/def ::prompt (spec/keys :req-un [::type ::message]
                              :opt-un [::on-cancel ::on-validate ::value]))
