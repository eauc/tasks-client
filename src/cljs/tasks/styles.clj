(ns tasks.styles
  (:require [garden.def :refer [defstyles]]
            [tasks.components.form.styles :refer [form-styles input-styles]]
            [tasks.components.nav.styles :refer [nav-styles]]
            [tasks.components.prompt.styles :refer [prompt-styles]]
            [tasks.components.task.styles :refer [task-styles]]
            [tasks.components.task-edit.styles :refer [task-edit-styles]]
            [tasks.components.tasks-list.styles :refer [tasks-list-styles tasks-create-styles]]
            [tasks.components.tasks-list-edit.styles :refer [tasks-list-edit-styles]]
            [tasks.styles.colors :refer [colors]]))

(defstyles screen
  [:* {:font-family "Droid Sans"
       :color (:default-text colors)}]
  [:body {:padding 0
          :margin 0}]
  form-styles
  [:.tasks
   {:align-items "center"
    :display "flex"
    :flex-flow "column nowrap"
    :height "100vh"
    :width "100vw"}
   [:&-header {:width "100%"}]
   [:&-body {:box-shadow "0 15px 15px rgba(0,0,0,0.3)"
             :display "flex"
             :flex-flow "column nowrap"
             :flex-grow "1"
             :max-width "800px"
             :position "relative"
             :width "100%"}]
   input-styles
   nav-styles
   prompt-styles
   task-styles
   tasks-create-styles
   task-edit-styles
   tasks-list-styles
   tasks-list-edit-styles])
