# Increasing resilience
- RabbitMQ is using hard-coded values rn, should probably be moved to env var.
  - (This isn't strictly resilience but.. should still maybe be done 😅)
- **Shipping Controller ignores errors that should def. cause a 500 rn!
- add retry to rabbitmq
- In entities, null, empty string and other sanity checks should be added