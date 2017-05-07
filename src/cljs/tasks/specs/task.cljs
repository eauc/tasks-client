(ns tasks.specs.task
  (:require [cljs.spec :as spec]
            [clojure.string :as str]))

(spec/def ::id
  (spec/and string?
            #(not (empty? (str/trim %)))))

(spec/def ::title
  (spec/and string?
            #(not (empty? (str/trim %)))))

(spec/def ::body string?)

(spec/def ::done boolean?)

(spec/def ::task
  (spec/keys :req-un [::id ::title ::body ::done]))

(spec/def ::tasks
  (spec/coll-of ::task))
