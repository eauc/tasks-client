(ns tasks.components.task.list-test
  (:require [cljs.spec :as spec]
            [cljs.spec.impl.gen :as gen]
            [clojure.test.check.generators]
            [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [tasks.components.task.list :as task-list]
            [tasks.models.task :as task]))

(defn toggle-details [state task]
  (let [new (:id task)]
    (update-in state [:show-details]
               #(if (= % new)
                  nil
                  new))))

(defcard-rg task-view
  (fn [state_atom _]
    (let [tasks (:tasks @state_atom)
          show-details (:show-details @state_atom)
          on-update #(swap! state_atom assoc-in [:tasks] %)
          toggle-details #(swap! state_atom toggle-details %)]
      [task-list/render tasks {:show-details show-details
                               :on-edit #(println "Edit! " %)
                               :on-update on-update
                               :toggle-details toggle-details}]))
  (reagent/atom {:tasks (gen/sample (spec/gen :tasks.models.task/task))
                 :show-details nil})
  {:inspect-data true})
