(ns tasks.components.task.create
  (:require [re-frame.core :as re-frame]
            [tasks.routes :as routes]))

(defn render [{:keys [on-create] :as props}]
  [:button.tasks-create {:type "button"
                         :on-click on-create}
   "+"])

(defn component []
  (fn []
    [render {:on-create #(re-frame/dispatch [:nav routes/create])}]))
