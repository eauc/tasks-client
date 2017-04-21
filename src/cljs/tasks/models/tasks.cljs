(ns tasks.models.tasks
  (:require [cljs.spec :as spec]
            [clojure.string :as str]))

(spec/def ::tasks (spec/* :tasks.models.task/task))

(spec/fdef delete-task
           :args (spec/cat :list #(spec/valid? ::tasks %)
                           :item :tasks.models.task/task)
           :ret ::tasks)
(defn delete-task [tasks task]
  (remove #(= (:id task) (:id %)) tasks))

(spec/fdef update-task
           :args (spec/cat :list #(spec/valid? ::tasks %)
                           :item :tasks.models.task/task)
           :ret ::tasks)
(defn update-task [tasks task]
  (map #(if (= (:id task) (:id %)) task %) tasks))

(spec/fdef filter-with
           :args (spec/cat :list #(spec/valid? ::tasks %)
                           :filter string?)
           :ret ::tasks)
(defn filter-with [tasks with]
  (let [re-with (re-pattern (str "(?i)"
                                 (-> with
                                     (str/trim)
                                     (str/replace #"\s+" "|")
                                     (str/replace #"\.+\*?" ".*"))))]
    (filter
      #(or (re-find re-with (:title %))
           (re-find re-with (:body %)))
      tasks)))

(spec/fdef sort-by-title
           :args (spec/cat :list #(spec/valid? ::tasks %))
           :ret ::tasks)
(defn sort-by-title [tasks]
  (sort-by (comp str/lower-case :title) tasks))
