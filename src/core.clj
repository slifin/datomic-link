(ns core
  (:require [mondo-clj.core :as monzo]
            [aero.core :refer [read-config]]))

(defn secrets []
  (read-config "src/secrets.edn"))

(comment
  (monzo/get-access-token (secrets)))

(comment
  "returns"
  {:access-token "access_token",
   :client-id "client_id",
   :expires-in 21600,
   :refresh-token "refresh_token",
   :token-type "Bearer",
   :user-id "user_id"})
