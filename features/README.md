# Aurum System Design Prep Guide

This folder turns real features from the Aurum service repos into interview-ready system design notes.

Foundation guides:
- [system-design-fundamentals.md](./system-design-fundamentals.md)
- [how-to-answer-in-interview.md](./how-to-answer-in-interview.md)
- [top-generic-system-design-problems.md](./top-generic-system-design-problems.md)

How to use this folder:
- Start with the files in order if you want a structured learning path.
- Use any single file as a standalone interview prep topic.
- Read the `How to explain in interview` section before mock interviews.
- Practice drawing the Mermaid diagram on paper or a whiteboard in simpler boxes.

How the features were selected:
- They appear to be implemented in the Aurum business services.
- They involve real distributed-system concerns like idempotency, webhooks, feature gating, data modeling, messaging, QR flows, and analytics.
- They are strong enough to discuss in SDE system-design rounds at product companies.

Services used as source material:
- `aurum-payment-service`
- `aurum-products-service`
- `aurum-store-service`
- `aurum-user-segmentation-service`

Feature list:
1. [01-payment-link-orchestration.md](./01-payment-link-orchestration.md)
2. [02-transaction-lifecycle-and-idempotency.md](./02-transaction-lifecycle-and-idempotency.md)
3. [03-payment-retry-orchestration.md](./03-payment-retry-orchestration.md)
4. [04-razorpay-webhook-processing.md](./04-razorpay-webhook-processing.md)
5. [05-subscription-plan-catalog.md](./05-subscription-plan-catalog.md)
6. [06-entitlement-and-feature-gating.md](./06-entitlement-and-feature-gating.md)
7. [07-invoice-generation-and-taxation.md](./07-invoice-generation-and-taxation.md)
8. [08-feature-overrides-for-tenants.md](./08-feature-overrides-for-tenants.md)
9. [09-product-catalog-management.md](./09-product-catalog-management.md)
10. [10-product-versioning-and-attachments.md](./10-product-versioning-and-attachments.md)
11. [11-bidding-marketplace.md](./11-bidding-marketplace.md)
12. [12-partial-payment-and-bid-settlement.md](./12-partial-payment-and-bid-settlement.md)
13. [13-store-onboarding-and-lifecycle.md](./13-store-onboarding-and-lifecycle.md)
14. [14-store-branch-management.md](./14-store-branch-management.md)
15. [15-store-offer-engine.md](./15-store-offer-engine.md)
16. [16-customer-enrollment-flow.md](./16-customer-enrollment-flow.md)
17. [17-gift-card-platform.md](./17-gift-card-platform.md)
18. [18-qr-connection-and-scan-journey.md](./18-qr-connection-and-scan-journey.md)
19. [19-price-protection-platform.md](./19-price-protection-platform.md)
20. [20-user-segmentation-engine.md](./20-user-segmentation-engine.md)
21. [21-notification-delivery-platform.md](./21-notification-delivery-platform.md)
22. [22-scheduled-notification-campaigns.md](./22-scheduled-notification-campaigns.md)
23. [23-role-based-access-control.md](./23-role-based-access-control.md)
24. [24-store-token-and-branch-scoped-permissions.md](./24-store-token-and-branch-scoped-permissions.md)
25. [25-audience-management-and-targeting.md](./25-audience-management-and-targeting.md)
26. [26-store-ad-campaign-platform.md](./26-store-ad-campaign-platform.md)
27. [27-exhibition-and-event-management.md](./27-exhibition-and-event-management.md)

Best order for beginners:
- Payment and webhook topics
- Subscription and feature-gating topics
- Product and bidding topics
- Store and QR topics
- Price protection and segmentation topics

Golden interview rule:
- Never only describe boxes.
- Always explain users, data, APIs, consistency, scale, failures, and tradeoffs.
