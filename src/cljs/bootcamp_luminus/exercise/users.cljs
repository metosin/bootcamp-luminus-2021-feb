(ns bootcamp-luminus.exercise.users
  (:require
    [day8.re-frame.http-fx]
    [reagent.dom :as rdom]
    [reagent.core :as r]
    [re-frame.core :as rf]
    [goog.events :as events]
    [goog.history.EventType :as HistoryEventType]
    [markdown.core :refer [md->html]]
    [bootcamp-luminus.ajax :as ajax]
    [bootcamp-luminus.events]
    [reitit.core :as reitit]
    [reitit.frontend.easy :as rfe]
    [clojure.string :as string]

    [stylefy.core :as stylefy]
    [bootcamp-luminus.exercise.http :as http]

    ["@material-ui/core/Button" :default Button])
  (:import goog.History))

(rf/reg-sub
  ::users
  (fn [db _]
    (::users db)))

(rf/reg-sub
  ::users-loading?
  (fn [db _]
    (::users-loading? db)))

(rf/reg-event-db
  ::set-users
  (fn [db [_ users]]
    (assoc db ::users users)))

(rf/reg-event-fx
  ::fetch-users
  (fn [_ _]
    (http/GET "/api/user"
              :on-success #(rf/dispatch [::set-users %]))))

(rf/reg-event-fx
  ::create-user
  (fn [_world [_ user]]
    (http/POST "/api/user"
               :params user
               :on-success #(rf/dispatch [::fetch-users]))))

(defn- user-list []
  (let [loading? @(rf/subscribe [::users-loading?])
        users @(rf/subscribe [::users])]
    (if loading?
      [:div "Loading.."]

      [:table
       [:thead
        [:tr
         [:th "Firstname"]
         [:th "Lastname"]
         [:th "Email"]]]
       [:tbody
        (doall
          (for [user users]
            ^{:key (str "table-row-" user)}
            [:tr
             [:td (:first-name user)]
             [:td (:last-name user)]
             [:td (:email user)]]))]])))

(defn- field-value [v]
  (-> v .-target .-value))

(defn- user-form []
  (let [new-user (atom {})]
    [:div
     [:form
      [:div.form-group
       [:label "Firstname"]
       [:input
        {:type :text
         :on-change #(swap! new-user assoc :first-name (field-value %))}]]
      [:div.form-group
       [:label "Lastname"]
       [:input
        {:type :text
         :on-change #(swap! new-user assoc :last-name (field-value %))}]]]
     [:> Button {:on-click #(rf/dispatch [::create-user @new-user])
                 :color "primary"
                 :variant "contained"}
      "Add"]]))

(defn page []
  (rf/dispatch [::fetch-users])
  (fn []
    [:div
     [user-form]
     [user-list]]))