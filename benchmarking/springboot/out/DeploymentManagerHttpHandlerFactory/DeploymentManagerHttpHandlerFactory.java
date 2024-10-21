import java.io.Closeable;
import java.io.IOException;

class DeploymentManagerHttpHandlerFactory implements HttpHandlerFactory {

    private final DeploymentManager deploymentManager;

    DeploymentManagerHttpHandlerFactory(DeploymentManager deploymentManager) {
        this.deploymentManager = deploymentManager;
    }

    public HttpHandler getHandler(HttpHandler next) {
        Assert.state(next == null, "DeploymentManagerHttpHandlerFactory must be first");
        return new DeploymentManagerHandler(this.deploymentManager);
    }

    DeploymentManager getDeploymentManager() {
        return this.deploymentManager;
    }
}
