(ns tasks.models.task
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

(spec/fdef toggle-done
           :args (spec/cat :task ::task)
           :ret ::task)
(defn toggle-done [task]
  (update-in task [:done] not))

(def edit-error-messages
  {:title "Title should be a non-empty string"
   :body "Body should be a string"})

(spec/fdef describe-errors
           :args (spec/cat :edit map?)
           :ret map?)
(defn describe-errors
  "Return a map of validation errors message for a task edit"
  [edit]
  (->> edit
       (spec/explain-data ::task)
       (:cljs.spec/problems)
       (map :path)
       (map (fn [path] [path (get-in edit-error-messages path)]))
       (reduce (fn [memo [path message]] (assoc-in memo path message)) {})))
