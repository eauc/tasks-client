(ns tasks.models.tasks
  (:require [cljs.spec :as spec]
            [clojure.string :as str]))

(spec/def ::tasks (spec/coll-of :tasks.models.task/task))

(spec/fdef delete-task
           :args (spec/cat :list ::tasks
                           :item :tasks.models.task/task)
           :ret ::tasks
           :fn #(>= (-> % (:args) (:list) count) (-> % (:ret) count)))
(defn delete-task
  "Delete :item in :list by :id"
  [tasks task]
  (remove #(= (:id task) (:id %)) tasks))

(spec/fdef update-task
           :args (spec/cat :list ::tasks
                           :item :tasks.models.task/task)
           :ret ::tasks
           :fn #(= (-> % (:args) (:list) count) (-> % (:ret) count)))
(defn update-task
  "Update :item in :list by :id"
  [tasks task]
  (map #(if (= (:id task) (:id %)) task %) tasks))

(spec/fdef toggle-done
           :args (spec/cat :list ::tasks)
           :ret ::tasks
           :fn #(= (-> % (:args) (:list) count) (-> % (:ret) count)))
(defn toggle-done
  "Check/Uncheck all tasks :done in :list"
  [tasks]
  (let [done (some-> tasks (first) (:done) (not))]
    (mapv #(assoc % :done done) tasks)))

(spec/fdef filter-with
           :args (spec/cat :list ::tasks
                           :filter string?)
           :ret ::tasks
           :fn #(>= (-> % (:args) (:list) count) (-> % (:ret) count)))
(defn filter-with
  "Filter :list tasks with :title or :body matching :filter"
  [tasks with]
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
           :args (spec/cat :list ::tasks)
           :ret ::tasks
           :fn #(= (-> % (:args) (:list) count) (-> % (:ret) count)))
(defn sort-by-title
  "Sort :list by title"
  [tasks]
  (sort-by (comp str/lower-case :title) tasks))
