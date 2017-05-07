(ns tasks.models.task
  (:require [cljs.spec :as spec]
            [clojure.string :as str]
            [tasks.specs.task :as task-spec]))

(spec/fdef toggle-done
           :args (spec/cat :task :tasks.specs.task/task)
           :ret :tasks.specs.task/task)
(defn toggle-done [task]
  (update-in task [:done] not))

(spec/fdef delete-task
           :args (spec/cat :list :tasks.specs.task/tasks
                           :item :tasks.specs.task/task)
           :ret :tasks.specs.task/tasks
           :fn #(>= (-> % (:args) (:list) count) (-> % (:ret) count)))
(defn delete-task
  "Delete :item in :list by :id"
  [tasks task]
  (remove #(= (:id task) (:id %)) tasks))

(spec/fdef update-task
           :args (spec/cat :list :tasks.specs.task/tasks
                           :item :tasks.specs.task/task)
           :ret :tasks.specs.task/tasks
           :fn #(= (-> % (:args) (:list) count) (-> % (:ret) count)))
(defn update-task
  "Update :item in :list by :id"
  [tasks task]
  (map #(if (= (:id task) (:id %)) task %) tasks))

(spec/fdef toggle-all-done
           :args (spec/cat :list :tasks.specs.task/tasks)
           :ret :tasks.specs.task/tasks
           :fn #(= (-> % (:args) (:list) count) (-> % (:ret) count)))
(defn toggle-all-done
  "Check/Uncheck all tasks :done in :list"
  [tasks]
  (let [done (some-> tasks (first) (:done) (not))]
    (mapv #(assoc % :done done) tasks)))

(spec/fdef filter-with
           :args (spec/cat :list :tasks.specs.task/tasks
                           :filter string?)
           :ret :tasks.specs.task/tasks
           :fn #(>= (-> % (:args) (:list) count) (-> % (:ret) count)))
(defn filter-with
  "Filter :list tasks with :title or :body matching :filter"
  [tasks with]
  (let [pattern (-> with
                    (str/trim)
                    (str/replace #"\s+" "|")
                    (str/replace #"\.+\*?" ".*"))
        re-with (re-pattern (str "(?i)" pattern))]
    (filter
     #(or (re-find re-with (:title %))
          (re-find re-with (:body %)))
     tasks)))

(spec/fdef sort-by-title
           :args (spec/cat :list :tasks.specs.task/tasks)
           :ret :tasks.specs.task/tasks
           :fn #(= (-> % (:args) (:list) count) (-> % (:ret) count)))
(defn sort-by-title
  "Sort :list by title"
  [tasks]
  (sort-by (comp str/lower-case :title) tasks))
