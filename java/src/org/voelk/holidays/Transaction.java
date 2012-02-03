package org.voelk.holidays;

public interface Transaction<Request extends RequestModel, Response extends ResponseModel> {
    Response execute(Request request);
}
