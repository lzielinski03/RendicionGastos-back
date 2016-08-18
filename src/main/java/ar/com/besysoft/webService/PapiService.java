package ar.com.besysoft.webService;

import ar.com.besysoft.entity.Instance;
import ar.com.besysoft.utils.Json;
import ar.com.besysoft.utils.ProcessWrapper;
import ar.com.besysoft.utils.StringId;
import stubs.*;

import java.util.List;

/**
 * Created by lzielinski on 04/08/2016.
 */
public class PapiService {

    private PapiConnection connection;

    public PapiService(String user, String pass) {
        this.connection = new PapiConnection(user, pass);
    }

    public ParticipantBean getCurrentParticipant() {
        ParticipantBean participant = null;
        try {
            participant = connection.papi.participantCurrent();
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }
        return participant;
    }

    public String getInstances() {
        ProcessWrapper wrapper = new ProcessWrapper();
        try {
            for (InstanceInfoBean instance : connection.papi.viewGetInstances("papi-bridge").getInstances()) {

                wrapper.addInstance(
                        instance.getProcess(),
                        connection.papi.processGet(instance.getProcess()).getName(),
                        instance.getActivityName(),
                        new Instance(
                                StringId.encode(instance.getId()),
                                instance.getDescription(),
                                instance.getState(),
                                instance.getReceptionTime(),
                                instance.getProcessDeadline()
                        )
                );
            }
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }
        return wrapper.getJSONProcess();
    }

    public String getInstanceInfo(String instanceId) {
        String result = "";
        try {
            String i_Info = connection.papi.instanceGetVariable(StringId.decode(instanceId), "i_Info");
            result = Json.StringFormat(i_Info.substring(1, i_Info.length()-1), ",", "=");
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String evaluateInstance(String instanceId, String isApprove) {
        System.out.println(instanceId);
        System.out.println(StringId.decode(instanceId));
        System.out.println(isApprove);

        InstanceInfoBean instance = null;
        try {
            instance = connection.papi.processGetInstance(StringId.decode(instanceId));
            System.out.println(instance.getId());
            System.out.println(instance.getActivityName());
            System.out.println(instance.getProcess());
            System.out.println(instance.getDescription());


            ArgumentsBean bean = new ArgumentsBean();

            ArgumentsBean.Arguments arg = new ArgumentsBean.Arguments();
            ArgumentsBean.Arguments.Entry entry = new ArgumentsBean.Arguments.Entry();
            entry.setKey("a_ExternalResult");
            entry.setValue(isApprove);
            arg.getEntry().add(entry);

            bean.setArguments(arg);

            connection.papi.processSendNotification(instance.getId(), "PapiBridgeListener", "PapiBridgeListenerIn", bean);
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deveria(InstanceInfoBean instance) {

        try {
            List<ArgumentSetBean> beginArgumentSets = connection.papi.processGet(instance.getProcess()).getBeginArgumentSets();

            for ( ArgumentSetBean x : beginArgumentSets) {
                System.out.println(x.getName());

                for (ArgumentBean f : x.getArguments()) {
                    System.out.println(f.getName());
                    System.out.println(f.getType());
                    System.out.println();
                }
                System.out.println();
            }

            ArgumentsBean bean = new ArgumentsBean();

            ArgumentsBean.Arguments arg = new ArgumentsBean.Arguments();
            ArgumentsBean.Arguments.Entry entry = new ArgumentsBean.Arguments.Entry();
            entry.setKey("a_ExternalResult");
            entry.setValue("Aprobo");
            arg.getEntry().add(entry);

            bean.setArguments(arg);

            connection.papi.processSendNotification(instance.getId(), "PapiBridgeListener", "PapiBridgeListenerIn", bean);

        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }
    }

    // activitie and instance
    public void nus() {
        String viewId = "ComprasConformaciónLegajoRRMM";
        try {
            System.out.println("View: " + connection.papi.viewGet(viewId).getId());
            for (InstanceInfoBean instance : connection.papi.viewGetInstances(viewId).getInstances()) {
                System.out.println("\tProcess: " + instance.getProcess());
                System.out.println("\tInstance: " + instance.getId());
                System.out.println("\t" + instance.getDescription());
                System.out.println("\t\tinstanceCanBeSelected:" + connection.papi.instanceCanBeSelected(instance.getId()));
                System.out.println("\t\tinstanceCanBeProcessed:" + connection.papi.instanceCanBeProcessed(instance.getId()));
                System.out.println("\t\tinstanceCanBeSent:" + connection.papi.instanceCanBeSent(instance.getId()));
                System.out.println();

                ArgumentsBean bean = new ArgumentsBean();

                ArgumentsBean.Arguments arg = new ArgumentsBean.Arguments();
                ArgumentsBean.Arguments.Entry entry = new ArgumentsBean.Arguments.Entry();
                entry.setKey("a_ResultadoAnalisis");
                entry.setValue("Aprobo");
                arg.getEntry().add(entry);

                bean.setArguments(arg);


                connection.papi.processSendNotification(instance.getProcess(), "Espera", "EsperaIn", bean);
                System.out.println("\tVisible activities from Process: " + connection.papi.processGetVisibleActivities(instance.getProcess()).getActivities().size());
                for (ActivityBean activitie : connection.papi.processGetVisibleActivities(instance.getProcess()).getActivities()) {
                    System.out.println("\t\t" + activitie.getName());
                    System.out.println("\t\tTasks:");
                    for (TaskBean task : activitie.getTasks()) {
                        System.out.println("\t\t\t" + task.getName());
                    }
                }
                System.out.println("----------------------------------------------------------");
            }
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }
    }
}
