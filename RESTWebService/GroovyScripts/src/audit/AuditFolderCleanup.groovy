package audit

/**
 * Description Here
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 14/03/14
 * @project RESTWebService
 */
class AuditFolderCleanup {

    static final String AUDIT_DIR = "D:\\RAD8\\Personal\\RESTAudit"
    static final String AUDIT_DIR_TEST = "D:\\RAD8\\Personal\\RESTAudit_TEST"

    public static void main(String[] args) {

        def directory = new File(AUDIT_DIR)

        long checkTime = new Date().minus(1).time

        File dir = new File(directory, new Date().minus(1).format("dd-MM-yyyy"))
        dir.mkdir()

        directory.eachFile { File auditFile ->

            if(auditFile.lastModified() >= checkTime) {
                println "moving " + auditFile.getName() + " to archived directory"
                auditFile.renameTo(new File(dir, auditFile.getName()))
            }

        }

    }

    private static void deleteAuditFile(String id) {

        def directory = new File(AUDIT_DIR_TEST)

        directory.eachFile { File auditFile ->

            def fullId = id + '.xml'

            if(auditFile.getName() == fullId) {
                auditFile.delete()
                println 'audit file deleted ' + fullId
            }

        }

    }

    private static void deleteAllAuditFiles() {

        def directory = new File(AUDIT_DIR_TEST)

        directory.eachFile { File auditFile ->
            auditFile.delete()
        }

    }

}