(ns tasks.components.task.view-test
  (:require [cljs.spec :as spec]
            [cljs.spec.impl.gen :as gen]
						[clojure.test.check.generators]
						[reagent.core :as reagent]
						[tasks.components.task.view :as task]
            [tasks.models.task])
	(:require-macros [devcards.core :as dc :refer [defcard-rg dom-node]]))

(defcard-rg task-view*
	(fn [state_atom _]
    [task/render-task-view
     (:task @state_atom)
     {:show-body (:show-body @state_atom)
      :toggle-body #(swap! state_atom update-in [:show-body] not)}])
  (reagent/atom {:task (gen/generate (spec/gen :tasks.models.task/task))
                 :show-body false})
  {:inspect-data true})
