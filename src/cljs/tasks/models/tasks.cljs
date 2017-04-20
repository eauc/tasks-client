(ns tasks.models.tasks
  (:require [cljs.spec :as spec]
            [tasks.models.task :as task]))

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
