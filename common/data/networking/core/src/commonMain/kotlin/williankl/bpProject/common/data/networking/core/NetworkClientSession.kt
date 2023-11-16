package williankl.bpProject.common.data.networking.core

public interface NetworkClientSession {
    public fun currentToken(): String?
    public fun attachBearer(token: String)
    public fun clearBearer()
}
