(ns core
  (:require [mondo-clj.core :as monzo]))

(comment
  (monzo/get-access-token {
                           :grant-type ""
                           :client-id
                           :client-name
                           :username
                           :password})

  (+ 1 1))


(comment
  "returns"
  {:access-token "access_token",
   :client-id "client_id",
   :expires-in 21600,
   :refresh-token "refresh_token",
   :token-type "Bearer",
   :user-id "user_id"})
