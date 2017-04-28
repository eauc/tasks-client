(ns tasks.models.list
  (:require [cljs.spec :as spec]
            [clojure.string :as str]))

(spec/def ::name (spec/and string?
                           (comp not empty? str/trim)))

(spec/def ::new-name string?)

(spec/def ::current-name (spec/nilable string?))

(spec/def ::edit
  (spec/keys :req-un [::new-name
                      ::current-name]))

(spec/def ::lists (spec/* name))

(spec/fdef create
           :args (spec/cat :lists #(spec/valid? ::lists %)
                           :name ::name)
           :ret ::lists)
(defn create [lists list]
  (into [] (sort (cons list lists))))

(spec/fdef delete
           :args (spec/cat :lists #(spec/valid? ::lists %)
                           :name ::name)
           :ret ::lists)
(defn delete [lists list]
  (into [] (sort (remove #(= list %) lists))))


(spec/fdef rename
           :args (spec/cat :lists #(spec/valid? ::lists %)
                           :old-name ::name
                           :new-name ::name)
           :ret ::lists)
(defn rename [lists old-name new-name]
  (->> lists
       (cons new-name)
       (remove #(= old-name %))
       (sort)
       (into [])))