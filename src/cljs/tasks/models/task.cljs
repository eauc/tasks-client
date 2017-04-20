(ns tasks.models.task
  (:require [cljs.spec :as spec]
            [clojure.string :as str]))

(spec/def ::id (spec/and string? #(not (empty? (str/trim %)))))

(spec/def ::title (spec/and string? #(not (empty? (str/trim %)))))

(spec/def ::body string?)

(spec/def ::done boolean?)

(spec/def ::task (spec/keys :req-un [::id ::title ::body ::done]))

(spec/fdef toggle-done
           :args (spec/cat :task ::task)
           :ret ::task)
(defn toggle-done [task]
  (update-in task [:done] not))
