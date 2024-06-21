# Re(s)trace - REST Open Telemetry Trace Collector for build pipelines

A super simple application to build Open Telemetry (OTEL) traces from REST APIs and send them off to a collector allowing
to process and visualize statistics about pipeline executions.

## Why?
- monitor and analyze your pipelines with same technology stack as your applications
- integrates well with existing pipelines since it's just a REST API call
- no need to install agents or modify your pipelines extensively
- very easy to deploy as a containerized application

## DORA Metrics
You can use this application to collect DORA metrics for your pipelines. DORA metrics are a set of metrics
that help you understand how your software delivery process is performing. They are based on the book
Accelerate by Nicole Forsgren, Jez Humble and Gene Kim. The metrics are:
- Deployment Frequency - how often you deploy to production
- Lead Time for Changes - how long it takes to go from code commit to code running in production
- Mean Time to Recovery - how long it takes to restore service after a defect which impairs users
- Change Failure Rate - how often you experience failures in production

With Restrace you can collect traces that allow you to calculate the first two:
- Deployment Frequency ✅
- Lead Time for Changes ✅

With the typical monitoring tools used for monitoring Service Level Objectives you can calculate Mean Time to Recovery.
Change Failure Rate is a bit more tricky to calculate, but you can use the traces to get an idea of how often
your pipelines fail - if that correlates with failures in production, then you have a good indicator.

## Next Steps
- [ ] Add more documentation, specifically a prepared Grafana board
- [ ] Add support for more metrics
- [ ] Add more configuration options
- [ ] Make it easier to queue up traces for sending when the collector is not available