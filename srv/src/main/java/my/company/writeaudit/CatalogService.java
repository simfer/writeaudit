package my.company.writeaudit;

import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cloud.sdk.cloudplatform.auditlog.AccessRequester;
import com.sap.cloud.sdk.cloudplatform.auditlog.AccessedAttribute;
import com.sap.cloud.sdk.cloudplatform.auditlog.AuditLogger;
import com.sap.cloud.sdk.cloudplatform.auditlog.AuditedDataObject;
import com.sap.cloud.sdk.cloudplatform.auditlog.AuditedDataSubject;
import com.sap.cloud.sdk.cloudplatform.auditlog.AccessedAttribute.Operation;

import org.springframework.stereotype.Component;

import cds.gen.catalogservice.Books;

import java.util.List;

import com.sap.cds.services.cds.CdsService;
import com.sap.cds.services.handler.EventHandler;
//import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.After;

@Component
@ServiceName("CatalogService")
public class CatalogService implements EventHandler {

    @After(event = CdsService.EVENT_READ, entity = "CatalogService.Books")
    public void readBooks(List<Books> books) {
        String userId = "simmaco";
        String identityZone = "1234";
        String ENTITY_ENDPOINT = "Books";
        String VIEW_CLOUDSERVICES = "All attributes";

        AuditLogger.logDataRead(new AccessRequester(userId, identityZone, null, null),
                new AuditedDataObject(ENTITY_ENDPOINT, (String) "Books"),
                new AuditedDataSubject("person", "user", userId), null,
                new AccessedAttribute(VIEW_CLOUDSERVICES, Operation.READ, "Read Books", null, books, true));

        System.out.println("Number of retrieved elements " + books.size());
    }

}